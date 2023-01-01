package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class Curiosity extends AbstractAwakenedCard {
    public final static String ID = makeID(Curiosity.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 3, 1

    public Curiosity() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null && m.getIntentBaseDmg() >= 0) {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, cardStrings.EXTENDED_DESCRIPTION[0], true));
                } else {
                    applyToSelfTop(new StrengthPower(p, magicNumber));
                }
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}