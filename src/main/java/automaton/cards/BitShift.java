package automaton.cards;

import automaton.FunctionHelper;
import basemod.BaseMod;
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
     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (FunctionHelper.isSequenceEmpty()) {
            cantUseMessage = masterUI.TEXT[3];
            return false;
        }
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> c = new ArrayList<>();
        for (AbstractCard r : FunctionHelper.held.group) {
            c.add(r.makeStatEquivalentCopy());
        }
        atb(new SelectCardsAction(c, 1, masterUI.TEXT[0], (cards) -> {
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
                    if (p.hand.size() <= BaseMod.MAX_HAND_SIZE) {
                        p.hand.addToTop(q);
                    } else {
                        p.discardPile.addToTop(q);
                    }
                }
            });
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}