package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import slimebound.SlimeboundMod;


public class Flail extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Flail";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/flail.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public Flail() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.STUDY_AUTOMATON);
        tags.add(SlimeboundMod.STUDY);

        this.baseDamage = 3;
        this.isMultiDamage = true;

        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;



    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));

        for (int i = 0; i < 2; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            //AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));


        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));

    }


    public AbstractCard makeCopy() {

        return new Flail();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(2);
            //upgradeMagicNumber(1);


        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


