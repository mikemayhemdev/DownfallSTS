package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SoulExchange extends AbstractSneckoCard {

    public final static String ID = makeID("SoulExchange");

    //stupid intellij stuff SKILL, SELF, RARE

    public SoulExchange() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(1));
        if (upgraded) atb(new DrawCardAction(1));
        atb(new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard q = cards.get(0);
            CardColor c = q.color;
            ArrayList<AbstractCard> coloredCards = new ArrayList<>();
            for (AbstractCard r : CardLibrary.getAllCards()) {
                if (r.color == c && r.rarity != CardRarity.SPECIAL && r.rarity != CardRarity.BASIC && !r.hasTag(CardTags.HEALING)) {
                    coloredCards.add(r);
                }
            }
            int x = p.hand.size();
            if (!coloredCards.isEmpty())
                for (int i = 0; i < x; i++) {
                    att(new MakeTempCardInHandAction(coloredCards.get(AbstractDungeon.cardRandomRng.random(0, coloredCards.size() - 1))));
                }
            att(new ExhaustAction(BaseMod.MAX_HAND_SIZE, true, false));
        }));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}