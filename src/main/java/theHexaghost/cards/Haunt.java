package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Haunt extends AbstractHexaCard {

    public final static String ID = makeID("Haunt");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Haunt() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(p, magicNumber));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard c : p.hand.group) {
                    if (!c.isEthereal) {
                        c.isEthereal = true;
                        if (!c.rawDescription.contains("Ethereal"))
                            c.rawDescription = "Ethereal. NL " + c.rawDescription;
                        c.initializeDescription();
                        c.superFlash(Color.PURPLE.cpy());
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}