package hermit.cards;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Grudge extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Grudge.class.getSimpleName());
    public static final String IMG = makeCardPath("grudge.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;

    private static final int DAMAGE = 9;

    // /STAT DECLARATION/

    public Grudge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        loadJokeCardImage(this, "grudge.png");
        isMultiDamage=true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, EnumPatch.HERMIT_GHOSTFIRE, true));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        int counter = 0;
        int realBaseDamage = baseDamage;

        counter += countCursesinGroup(AbstractDungeon.player.drawPile);
        counter += countCursesinGroup(AbstractDungeon.player.discardPile);
        counter += countCursesinGroup(AbstractDungeon.player.hand);
        baseDamage += counter*magicNumber;

        super.calculateCardDamage(mo);

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    public void applyPowers() {
        int counter = 0;
        int realBaseDamage = baseDamage;

        counter += countCursesinGroup(AbstractDungeon.player.drawPile);
        counter += countCursesinGroup(AbstractDungeon.player.discardPile);
        counter += countCursesinGroup(AbstractDungeon.player.hand);
        baseDamage += counter*magicNumber;

        super.applyPowers();

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    private int countCursesinGroup(CardGroup cardGroup) {
        int counter=0;
        for (AbstractCard c : cardGroup.group)
        {
            if (c.color==CardColor.CURSE) {
                counter++;
            }
        }

        return counter;
    }
}