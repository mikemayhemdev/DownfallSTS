package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import slimebound.SlimeboundMod;
import theHexaghost.HexaMod;

public class ShadowStrike extends AbstractHexaCard {

    public final static String ID = makeID("ShadowStrike");

    private AbstractCard parent;

    public ShadowStrike(AbstractCard parent) {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 3;
        selfRetain = true;
        exhaust = true;
//        cardsToPreview = new NightmareGuise();
        tags.add(CardTags.STRIKE);
//        setParent(parent);
        HexaMod.loadJokeCardImage(this, "ShadowStrike.png");
    }

    public ShadowStrike() {
        this(null);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    //    public void setParent(AbstractCard parent) {
//        this.parent = parent;
//        if (parent != null)
//            cardsToPreview = this.parent.makeStatEquivalentCopy();
//    }

//        superFlash(Color.PURPLE);
//        AbstractCard q = new NightmareGuise();
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

//    @Override
//    public AbstractCard makeStatEquivalentCopy() {
//        ShadowStrike card = (ShadowStrike) this.makeCopy();
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
