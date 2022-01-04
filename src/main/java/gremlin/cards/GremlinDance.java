package gremlin.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.actions.ShackleAction;
import gremlin.characters.GremlinCharacter;
import gremlin.powers.WizPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static gremlin.GremlinMod.*;

public class GremlinDance extends AbstractGremlinCard {
    public static final String ID = getID("GremlinDance");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_dance.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int BLOCK = 6;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 3;

    private String gremlin;
    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public GremlinDance()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

        this.baseDamage = POWER;
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.gremlin = "";

        cardsList.add(new GremlinDance("angry"));
        cardsList.add(new GremlinDance("fat"));
        cardsList.add(new GremlinDance("shield"));
        cardsList.add(new GremlinDance("sneak"));
        cardsList.add(new GremlinDance("wizard"));
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public GremlinDance(String gremlin) {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);

        this.baseDamage = POWER;
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.gremlin = gremlin;
        updateContents();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
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
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }

        if(gremlin.equals("angry")){
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
                    this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        } else if(gremlin.equals("wizard")){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        if(gremlin.equals("fat")){
            AbstractDungeon.actionManager.addToBottom(new ShackleAction(m, magicNumber));
        }

        if(gremlin.equals("sneak")){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        }

        if(gremlin.equals("wizard")){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new WizPower(p, this.magicNumber), this.magicNumber));
        }

        if(isNob){
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardsList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardsList.get(previewIndex);
                }
                if (previewIndex == cardsList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public AbstractCard makeCopy()
    {
        if (this.gremlin.equals("")) {
            return new GremlinDance();
        } else {
            return new GremlinDance(this.gremlin);
        }
    }

    @Override
    public void applyPowers() {
        updateContents();
        super.applyPowers();
    }

    private void updateContents(){
        this.tags.remove(MAD_GREMLIN);
        this.tags.remove(FAT_GREMLIN);
        this.tags.remove(SHIELD_GREMLIN);
        this.tags.remove(SNEAKY_GREMLIN);
        this.tags.remove(WIZARD_GREMLIN);
        this.tags.remove(NOB_GREMLIN);
        this.rawDescription = strings.EXTENDED_DESCRIPTION[0];
        if(!this.gremlin.equals("") || AbstractDungeon.player instanceof GremlinCharacter){
            if(this.gremlin.equals("") && ((GremlinCharacter) AbstractDungeon.player).nob){
                rawDescription += strings.EXTENDED_DESCRIPTION[6];
                this.isMultiDamage = false;
                this.target = CardTarget.ENEMY;
                this.wizardry = false;
                this.tags.add(NOB_GREMLIN);
                setBackgrounds();
            } else {
                String gremlin = this.gremlin;
                if (gremlin.equals("")) {
                    gremlin = ((GremlinCharacter) AbstractDungeon.player).currentGremlin;
                }
                switch (gremlin) {
                    case "angry":
                        rawDescription += strings.EXTENDED_DESCRIPTION[1];
                        this.isMultiDamage = true;
                        this.target = CardTarget.ALL_ENEMY;
                        this.wizardry = false;
                        this.tags.add(MAD_GREMLIN);
                        setBackgrounds();
                        break;
                    case "fat":
                        rawDescription += strings.EXTENDED_DESCRIPTION[2];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(FAT_GREMLIN);
                        setBackgrounds();
                        break;
                    case "shield":
                        rawDescription += strings.EXTENDED_DESCRIPTION[3];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(SHIELD_GREMLIN);
                        setBackgrounds();
                        break;
                    case "sneak":
                        rawDescription += strings.EXTENDED_DESCRIPTION[4];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(SNEAKY_GREMLIN);
                        setBackgrounds();
                        break;
                    case "wizard":
                        rawDescription += strings.EXTENDED_DESCRIPTION[5];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        this.tags.add(WIZARD_GREMLIN);
                        setBackgrounds();
                        break;
                }
            }
        }
        initializeDescription();
    }

    @Override
    public void onGremlinSwapInDeck() {
        updateContents();
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }
}
