package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArenaPreparation extends AbstractChampCard {

    public final static String ID = makeID("ArenaPreparation");

    //stupid intellij stuff skill, self, uncommon

    public ArenaPreparation() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        myHpLossCost = 8;
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        fatigue(8);
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
            c.freeToPlayOnce = true;
            makeInHand(c);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}