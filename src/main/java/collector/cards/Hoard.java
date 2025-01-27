package collector.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.PropertiesMod;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class Hoard extends AbstractCollectorCard {
    public final static String ID = makeID(Hoard.class.getSimpleName());

    public Hoard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : DrawCardAction.drawnCards) {
                    q.superFlash(Color.GREEN.cpy());
                    PropertiesMod mod = new PropertiesMod();
                    if (!q.selfRetain)
                        mod.addProperty(PropertiesMod.supportedProperties.RETAIN, true);
                    if (!mod.bonusPropertiesForThisTurn.isEmpty())
                        CardModifierManager.addModifier(q, mod);
                }
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}