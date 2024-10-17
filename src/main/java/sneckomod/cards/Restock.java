package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

public class Restock extends AbstractSneckoCard {

    public final static String ID = makeID("Restock");

    //stupid intellij stuff SKILL, SELF, RARE


    public Restock() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        tags.add(SneckoMod.RNG);
        this.exhaust = true;
        SneckoMod.loadJokeCardImage(this, "Restock.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DiscardAction(p, p, p.hand.size(), true));
        atb(new DrawCardAction(getRandomNum(5, 10, this)));
        atb(new MuddleHandAction()); // it's 12% cooler now.
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}