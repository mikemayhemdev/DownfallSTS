package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class TendrilStrike extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:TendrilStrike";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/tendrilflail.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    private boolean returnThis;
    private int timesReturnedThisTurn = 0;
    private int timesReturnedAllowed = 1;


    public TendrilStrike() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 5;
        this.returnThis = false;
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.exhaust = true;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (m.hasPower("Poison")) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
            m.getPower("Poison").atStartOfTurn();


            if (upgraded && m.getPower("Poison").amount > 1) {
                m.getPower("Poison").atStartOfTurn();


            }

        }
    }

    public AbstractCard makeCopy() {

        return new TendrilStrike();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            //upgradeDamage(1);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


