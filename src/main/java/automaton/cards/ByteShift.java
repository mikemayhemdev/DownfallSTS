package automaton.cards;

import automaton.FunctionHelper;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.RetainCardMod;

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
                isDone = true;
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        FunctionHelper.genPreview();
                    }
                });
                for (AbstractCard r : FunctionHelper.held.group) {
                    att(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            r.superFlash();
                            CardModifierManager.addModifier(r, new RetainCardMod());
                        }
                    });
                    att(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            FunctionHelper.held.removeCard(r);
                            if (p.hand.size() <= BaseMod.MAX_HAND_SIZE) {
                                p.hand.addToTop(r);
                            } else {
                                p.discardPile.addToTop(r);
                            }
                        }
                    });
                }
            }
        });

    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}