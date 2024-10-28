package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import static collector.util.Wiz.atb;
import static sneckomod.cards.AbstractSneckoCard.makeID;

public class Belittle extends AbstractSneckoCard {

    public static final String ID = makeID("Belittle");

    // Card constants
    private static final int MAGIC = 9;
    private static final int UPGRADE_MAGIC = 3;

    public Belittle() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "Belittle.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractPower q : m.powers) {
                    if (q.type == AbstractPower.PowerType.DEBUFF) {
                        atb(new LoseHPAction(m, p, magicNumber));
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
