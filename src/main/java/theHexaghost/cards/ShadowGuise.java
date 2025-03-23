package theHexaghost.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class ShadowGuise extends AbstractHexaCard {

    public final static String ID = makeID("ShadowGuise");

    private AbstractCard parent;

    //Shadow Guise
    public ShadowGuise(AbstractCard parent) {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = 4;
        selfRetain = true;
        exhaust = true;
//        setParent(parent);
//        cardsToPreview = new NightmareStrike();
        HexaMod.loadJokeCardImage(this, "ShadowGuise.png");
    }

    public ShadowGuise() {
        this(null);
    }

    public void setParent(AbstractCard parent) {
        this.parent = parent;
        if (parent != null)
            cardsToPreview = this.parent.makeStatEquivalentCopy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        superFlash(Color.PURPLE);
//        AbstractCard q = new NightmareStrike();
//        if (upgraded) q.upgrade();
//        atb(new MakeTempCardInHandAction(q));
//        atb(new AbstractGameAction() {
//            @Override
//            public void update() {
//                isDone = true;
//                if (parent != null && p.exhaustPile.contains(parent)) {
//                    att(new AbstractGameAction() {
//                        @Override
//                        public void update() {
//                            isDone = true;
//                            p.exhaustPile.removeCard(parent);
//                            AbstractDungeon.effectsQueue.add(new ShowCardAndAddToDiscardEffect(parent.makeSameInstanceOf()));
//                        }
//                    });
//                }
//            }
//        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


//    @Override
//    public void update() {
//        super.update();
//        if (hb.hovered) {
//            cardsToPreview = new NightmareStrike();
//            if(upgraded) cardsToPreview.upgrade();
//        }
//    }

//    @Override
//    public AbstractCard makeStatEquivalentCopy() {
//        ShadowGuise card = (ShadowGuise) this.makeCopy();
//
//        for(int i = 0; i < this.timesUpgraded; ++i) {
//            card.upgrade();
//        }
//
//        card.name = this.name;
//        card.target = this.target;
//        card.upgraded = this.upgraded;
//        card.timesUpgraded = this.timesUpgraded;
//        card.baseDamage = this.baseDamage;
//        card.baseBlock = this.baseBlock;
//        card.baseMagicNumber = this.baseMagicNumber;
//        card.cost = this.cost;
//        card.costForTurn = this.costForTurn;
//        card.isCostModified = this.isCostModified;
//        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
//        card.inBottleLightning = this.inBottleLightning;
//        card.inBottleFlame = this.inBottleFlame;
//        card.inBottleTornado = this.inBottleTornado;
//        card.isSeen = this.isSeen;
//        card.isLocked = this.isLocked;
//        card.misc = this.misc;
//        card.freeToPlayOnce = this.freeToPlayOnce;
//        card.setParent(this.parent);
//        return card;
//    }


}
