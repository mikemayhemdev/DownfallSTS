package awakenedOne.cards;

import awakenedOne.powers.DarkIncantationRitualPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class CawCaw extends AbstractAwakenedCard {
    public final static String ID = makeID(CawCaw.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 1

    public CawCaw() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", -.1f);

        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, Byrd.DIALOG[0], true));
        applyToSelf(new DarkIncantationRitualPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}