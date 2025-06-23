package awakenedOne.cards;

import awakenedOne.powers.DarkIncantationRitualPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class MazalethForm extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MazalethForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 1;
        isEthereal = true;
        loadJokeCardImage(this, makeBetaCardPath(MazalethForm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", -.1f);
        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, Byrd.DIALOG[0], true));
        applyToSelf(new RitualPower(p, magicNumber, true));
        applyToSelf(new DarkIncantationRitualPower(magicNumber));
    }

    public void upp() {
        isEthereal = false;
    }
}