package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class PureSnecko extends AbstractSneckoCard {

    public final static String ID = SneckoMod.makeID("PureSnecko");

    //I'm sorry but this card just HAD to be changed, the action still exists though

    // SKILL, SELF, UNCOMMON
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public PureSnecko() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;

        SneckoMod.loadJokeCardImage(this, "PureSnecko.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c: DrawCardAction.drawnCards)
                    Wiz.att(new MuddleAction(c));
                isDone = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            initializeDescription();
        }
    }
}
