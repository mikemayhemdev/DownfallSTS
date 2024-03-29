package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

@Deprecated
public class zzzSyncronizedStrike extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("zzzSyncronizedStrike");
    public static final String NAME;
    public static final String IMG_PATH = "cards/syncStrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 8;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 3;
    private static final int HEAL = 5;
    private static final int UPGRADE_HEAL = 7;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public zzzSyncronizedStrike() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = HEAL;
        this.tags.add(CardTags.STRIKE);

//this.sockets.add(GuardianMod.socketTypes.RED);


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //if (GuardianMod.bronzeOrbInPlay != null){
        //  GuardianMod.bronzeOrbInPlay.takeTurn();
        //  AbstractDungeon.actionManager.addToBottom(new HealAction(GuardianMod.bronzeOrbInPlay, p, this.magicNumber));

        //}
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new zzzSyncronizedStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_HEAL);
        }


    }
}


