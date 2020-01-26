package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class PressTheAttack extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:PressTheAttack";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/strikeweak.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public PressTheAttack() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.GOOPEXPLOIT);

        this.baseDamage = 8;



    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractOrb oldestOrb = null;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                oldestOrb = o;
                break;
            }

        }



        if (m.hasPower(SlimedPower.POWER_ID)) {

                if (oldestOrb != null) {
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new TrigggerSpecificSlimeAttackAction(oldestOrb));
                    if (upgraded)  com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new TrigggerSpecificSlimeAttackAction(oldestOrb));

                }

    }
}




    public AbstractCard makeCopy() {

        return new PressTheAttack();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(2);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


