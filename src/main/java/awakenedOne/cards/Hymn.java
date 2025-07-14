package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.Wiz;
import collector.powers.AddCopyNextTurnPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.Drained;

import java.util.Collections;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;
import static collector.util.Wiz.applyToSelfTop;


public class Hymn extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Hymn.class.getSimpleName());

    public Hymn() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(Hymn.class.getSimpleName() + ".png"));
        baseBlock = 3;
        this.cardsToPreview = new Ceremony();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard c = new Ceremony();
        if (upgraded) c.upgrade();
        Wiz.atb(new MakeTempCardInHandAction(c, 1));
        this.addToBot(new ApplyPowerAction(p, p, new Drained(p,p, 1), 1));
    }

    @Override
    public void upp() {
        upgradeBlock(1);
        cardsToPreview.upgrade();
    }
}