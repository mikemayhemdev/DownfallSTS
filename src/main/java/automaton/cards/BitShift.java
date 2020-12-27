package automaton.cards;

import automaton.FunctionHelper;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.RetainCardMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class BitShift extends AbstractBronzeCard {

    public final static String ID = makeID("BitShift");

    //stupid intellij stuff skill, self, common

    public BitShift() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> c = new ArrayList<>();
        for (AbstractCard r : FunctionHelper.held.group) {
            c.add(r.makeStatEquivalentCopy());
        }
        atb(new SelectCardsAction(c, 1, "Choose.", (cards) -> { //TODO: Localize
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    FunctionHelper.genPreview();
                }
            });
            AbstractCard q = cards.get(0);
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    q.superFlash();
                    CardModifierManager.addModifier(q, new RetainCardMod());
                }
            });
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    FunctionHelper.held.removeCard(q.cardID);
                    for (int i = 0; i < FunctionHelper.held.size(); i++) {
                        FunctionHelper.held.group.get(i).target_x = FunctionHelper.cardPositions[i].x;
                        FunctionHelper.held.group.get(i).target_y = FunctionHelper.cardPositions[i].y;
                    }
                    p.hand.addToTop(q);
                }
            });
        }));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}