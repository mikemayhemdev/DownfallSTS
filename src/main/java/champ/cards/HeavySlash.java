package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class HeavySlash extends AbstractChampCard {

    public final static String ID = makeID("HeavySlash");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public HeavySlash() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        // tags.add(ChampMod.TECHNIQUE);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL);
        c.isSeen = true;
        UnlockTracker.markCardAsSeen(c.cardID);
        c.modifyCostForCombat(-99);
        if (upgraded) c.upgrade();
        makeInHand(c);
        //   techique();
    }


    public void upp() {
        upgradeDamage(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();

    }
}