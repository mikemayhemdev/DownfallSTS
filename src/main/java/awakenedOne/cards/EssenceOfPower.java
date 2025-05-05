package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PowerPotion;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
@Deprecated
@CardIgnore
public class EssenceOfPower extends AbstractAwakenedCard {
    public final static String ID = makeID(EssenceOfPower.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , 6, 2

    public EssenceOfPower() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        tags.add(CardTags.HEALING);
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPotion potion = new PowerPotion();
        if (AbstractDungeon.player.hasRelic("Sozu")) {
            AbstractDungeon.player.getRelic("Sozu").flash();
        } else {
            AbstractDungeon.player.obtainPotion(potion.makeCopy());
        }
    }

    public void upp() {
    this.isEthereal = false;
    }
}