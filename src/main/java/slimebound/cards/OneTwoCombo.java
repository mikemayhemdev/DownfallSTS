package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.ExhaustToHandDirectlyAction;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;


public class OneTwoCombo extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:OneTwoCombo";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/acidtongue.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public OneTwoCombo() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        baseDamage = 6;
        exhaust = true;
    }

    public void onSplit() {
        addToBot(new ExhaustToHandDirectlyAction(this));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractOrb oldestOrb = null;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                oldestOrb = o;
                break;
            }

        }
        addToBot(new TrigggerSpecificSlimeAttackAction(oldestOrb));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}

