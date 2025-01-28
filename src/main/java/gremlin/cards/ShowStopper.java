package gremlin.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import gremlin.powers.WizPower;
import sneckomod.SneckoMod;

import static gremlin.GremlinMod.WIZARD_GREMLIN;

public class ShowStopper extends AbstractGremlinCard {
    public static final String ID = getID("ShowStopper");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/show_stopper.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 0;
    private static final int POWER = 0;
    private static final int UPGRADE_BONUS = 2;
    private static final int SHOWMANSHIP = 7;

    public ShowStopper()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = 5;
        this.isMultiDamage = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(WIZARD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "ShowStopper.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GrandFinalEffect(), 1.0F));
        for(int i=0;i<this.magicNumber;i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                            AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!p.hasPower(WizPower.POWER_ID) || p.getPower(WizPower.POWER_ID).amount < SHOWMANSHIP)
        {
            this.cantUseMessage = strings.EXTENDED_DESCRIPTION[0];
            return false;
        } else if(p.getPower(WizPower.POWER_ID).amount > SHOWMANSHIP){
            this.cantUseMessage = strings.EXTENDED_DESCRIPTION[1];
            return false;
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
upgradeMagicNumber(1);
        }
    }
}
