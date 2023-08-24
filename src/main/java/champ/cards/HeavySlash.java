package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static champ.ChampMod.loadJokeCardImage;

public class HeavySlash extends AbstractChampCard {
    public final static String ID = makeID("HeavySlash");

    public HeavySlash() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 10;
        postInit();
        loadJokeCardImage(this, "HeavySlash.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        c.isSeen = true;
        UnlockTracker.markCardAsSeen(c.cardID);
        c.modifyCostForCombat(-99);
        if (upgraded) c.upgrade();
        makeInHand(c);
    }


    public void upp() {
        upgradeDamage(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}