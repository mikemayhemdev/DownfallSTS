package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;


public class TagTeam extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:TagTeam";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/zzzeventtackle.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public TagTeam() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 3;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        int bonus = 0;
        if (upgraded) bonus = this.magicNumber;
        addToBot(new SlimeSpawnAction(new AttackSlime(), false, true, bonus, 0));
        AbstractOrb oldestOrb = null;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                oldestOrb = o;
                break;
            }

        }
        addToBot(new TrigggerSpecificSlimeAttackAction(oldestOrb));
        addToBot(new TrigggerSpecificSlimeAttackAction(oldestOrb));
        addToBot(new TrigggerSpecificSlimeAttackAction(oldestOrb));
    }

    public AbstractCard makeCopy() {
        return new TagTeam();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}

