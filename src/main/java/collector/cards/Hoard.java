package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.HoardedMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Hoard extends AbstractCollectorCard {
    public final static String ID = makeID(Hoard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 2, 1

    public Hoard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
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
                        for (AbstractCard q : DrawCardAction.drawnCards) {
                            q.superFlash(Color.GREEN.cpy());
                            CardModifierManager.addModifier(q, new HoardedMod());
                        }
                    }
                });
                att(new DrawCardAction(2));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}