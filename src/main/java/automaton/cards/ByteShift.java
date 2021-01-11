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

import java.util.ArrayList;

public class ByteShift extends AbstractBronzeCard {

    public final static String ID = makeID("ByteShift");

    //stupid intellij stuff skill, self, common

    public ByteShift() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {

                    for (AbstractCard r : FunctionHelper.held.group) {
                        isDone = true;
                        FunctionHelper.held.removeCard(r.cardID);
                        for (int i = 0; i < FunctionHelper.held.size(); i++) {
                            FunctionHelper.held.group.get(i).target_x = FunctionHelper.cardPositions[i].x;
                            FunctionHelper.held.group.get(i).target_y = FunctionHelper.cardPositions[i].y;
                        }
                        CardModifierManager.addModifier(r, new RetainCardMod());
                        if (p.hand.size() <= BaseMod.MAX_HAND_SIZE) {
                            p.hand.addToTop(r);
                        } else {
                            p.discardPile.addToTop(r);
                        }
                    }
                    FunctionHelper.genPreview();
                }
            });

    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}