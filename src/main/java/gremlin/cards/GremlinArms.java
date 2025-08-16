package gremlin.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.GremlinMod;
import gremlin.actions.ShackleAction;
import gremlin.cards.pseudocards.*;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.*;
import gremlin.powers.ModifiedLoseStrengthPower;
import gremlin.powers.WizPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static gremlin.GremlinMod.SNEAKY_GREMLIN;

public class GremlinArms extends AbstractGremlinCard {
    public static final String ID = getID("GremlinArms");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_arms.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_BONUS = 1;
    private boolean hasOptions = true;
    private float rotationTimer;
    private int previewIndex;
    private final ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public GremlinArms()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        GremlinMod.loadJokeCardImage(this, "GremlinArms.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {

        if(hasOptions){
            addToBot(new ChooseOneAction(updateModal()));
        }

        for(int i = 0; i < magicNumber; i++){
            String gremlin = "";
            boolean isNob = false;
            if(AbstractDungeon.player instanceof GremlinCharacter) {
                if(((GremlinCharacter) AbstractDungeon.player).nob){
                    isNob = true;
                } else {
                    gremlin = ((GremlinCharacter) AbstractDungeon.player).currentGremlin;
                }
            }

            if(gremlin.equals("shield")){
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, 2));
            }

            if(gremlin.equals("angry")){
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                        new StrengthPower(p, MadGremlin.STRENGTH)));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                        new ModifiedLoseStrengthPower(p, MadGremlin.STRENGTH)));
            }

            else if(gremlin.equals("wizard")){
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                        new WizPower(p, 1), 1));
            } else {
               // AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                  //      this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }

            if(gremlin.equals("fat")){
                for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p,
                                new WeakPower(mo, 1, false), 1));
                    }
                }
            }

            if(gremlin.equals("sneak")){
                AbstractDungeon.actionManager.addToTop(
                        new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, SneakyGremlin.DAMAGE, DamageInfo.DamageType.THORNS),
                                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

    private ArrayList<AbstractCard> updateModal(){
        if(AbstractDungeon.player != null){
            ArrayList<GremlinStandby> living = new ArrayList<>();
            for(AbstractOrb orb : AbstractDungeon.player.orbs){
                if(orb instanceof GremlinStandby){
                    living.add((GremlinStandby) orb);
                }
            }
            if(living.size() == 0){
                hasOptions = false;
                return new ArrayList<>();
            }
            else {
                hasOptions = true;
                ArrayList<AbstractCard> options = new ArrayList<>();
                for(AbstractOrb grem : living){
                    if(grem instanceof MadGremlin){
                        options.add(new MadGremlinCard());
                    }
                    else if(grem instanceof FatGremlin){
                        options.add(new FatGremlinCard());
                    }
                    else if(grem instanceof ShieldGremlin){
                        options.add(new ShieldGremlinCard());
                    }
                    else if(grem instanceof SneakyGremlin){
                        options.add(new SneakyGremlinCard());
                    }
                    else if(grem instanceof GremlinWizard){
                        options.add(new GremlinWizardCard());
                    }
                }
                return options;
            }
        }
        hasOptions = false;
        return new ArrayList<>();
    }

    @Override
    public void applyPowers() {
        updateModal();
        super.applyPowers();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}

