package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.ExhumeFunctionAction;
import automaton.actions.ScryEncodeCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Assembly extends AbstractBronzeCard {

    public final static String ID = makeID("Assembly");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public Assembly() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Assembly.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    ArrayList<AbstractCard> valid = new ArrayList<>();
                    valid.addAll(AbstractDungeon.player.exhaustPile.group.stream().filter(q -> q instanceof FunctionCard).collect(Collectors.toList()));
                    if (!valid.isEmpty()) {
                        AbstractCard c = valid.get(AbstractDungeon.cardRandomRng.random(0,valid.size()-1));
                        p.exhaustPile.removeCard(c);
                        p.hand.addToBottom(c);
                        c.freeToPlay();
                    }
                }
            });
        }

    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}