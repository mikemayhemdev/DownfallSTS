package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theHexaghost.HexaMod;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class EtherealExpedition extends AbstractHexaCard {

    public final static String ID = makeID("EtherealExpedition");

    //eerie expedition

    public EtherealExpedition() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        exhaust = false;
        baseMagicNumber = magicNumber = 1;
//        baseBurn = burn = 1;
        HexaMod.loadJokeCardImage(this, "EtherealExpedition.png");
    }

    public static AbstractCard returnTrulyRandomEtherealCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();// 1201
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }

        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }
        if (list.isEmpty()) {
            list.add(new PowerFromBeyond());
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = 0; i < this.magicNumber; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInHandAction(q));
        }
        for (int i = 0; i < 1; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInDrawPileAction(q, 1, true, true));
        }
//        q.setCostForTurn(0);
//        this.addToBot(new MakeTempCardInHandAction(q, true));// 34
    }

    public void afterlife() {
        for (int i = 0; i < 1; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInDrawPileAction(q, 1, true, true));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}