package theHexaghost.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.EtherealMod;

public class Haunt extends AbstractHexaCard {

    public final static String ID = makeID("Haunt");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    public static final String EXTENDED_DESCRIPTION[] = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public Haunt() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
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
                        CardModifierManager.addModifier(c, new EtherealMod());
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