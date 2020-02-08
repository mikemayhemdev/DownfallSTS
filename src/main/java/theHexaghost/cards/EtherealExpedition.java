package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class EtherealExpedition extends AbstractHexaCard {

    public final static String ID = makeID("EtherealExpedition");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public EtherealExpedition() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    private static AbstractCard returnTrulyRandomEtherealCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();// 1201
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.isEthereal && !c.hasTag(CardTags.HEALING)) {// 1203
                list.add(c);// 1204
            }
        }

        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.isEthereal && !c.hasTag(CardTags.HEALING)) {// 1203
                list.add(c);// 1204
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.isEthereal && !c.hasTag(CardTags.HEALING)) {// 1203
                list.add(c);// 1204
            }
        }
        if (list.isEmpty()) {
            list.add(new PowerFromBeyond());
        }
        return (AbstractCard) list.get(cardRandomRng.random(list.size() - 1));// 1217
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
        q.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(q, true));// 34
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}