package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class Middens extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Middens.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Middens() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

        public void use(AbstractPlayer p, AbstractMonster m) {
            blck();
            atb(new MultiGroupSelectAction(
                    cardStrings.EXTENDED_DESCRIPTION[magicNumber == 1 ? 0 : 1],
                    (cards, groups) -> {
                        Collections.reverse(cards);
                        cards.forEach(c -> att(new AbstractGameAction() {
                            public void update() {
                                isDone = true;
                                if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                                    if (groups.get(c) == p.drawPile)
                                        p.drawPile.moveToDiscardPile(c);
                                    p.createHandIsFullDialog();
                                } else
                                    p.hand.moveToHand(c, groups.get(c));
                            }
                        }));
                    },
                    magicNumber, false, c -> c.type == CardType.STATUS, CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE
            ));

        }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}