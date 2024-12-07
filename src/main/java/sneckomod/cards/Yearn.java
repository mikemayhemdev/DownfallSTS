package sneckomod.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cardmods.PropertiesMod;
import sneckomod.SneckoMod;

@Deprecated
@CardIgnore
public class Yearn extends AbstractSneckoCard {

    public final static String ID = makeID("Yearn");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 2;

    public Yearn() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "Yearn.png");
    }

    // code taken from hoard but modified
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : DrawCardAction.drawnCards) {
                    q.superFlash(Color.GREEN.cpy());
                    PropertiesMod mod = new PropertiesMod();
                    if (!q.selfRetain)
                        mod.addProperty(PropertiesMod.supportedProperties.RETAIN, false);
                    if (!mod.bonusPropertiesForThisTurn.isEmpty())
                        CardModifierManager.addModifier(q, mod);
                }
            }
        }));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}