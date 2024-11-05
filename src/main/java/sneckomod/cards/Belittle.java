package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.util.Wiz.atb;
import static sneckomod.cards.AbstractSneckoCard.makeID;

public class Belittle extends AbstractSneckoCard implements OnObtainCard{

    public static final String ID = makeID("Belittle");

    // Card constants
    private static final int MAGIC = 9;
    private static final int UPGRADE_MAGIC = 3;

    public Belittle() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
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
                    }
                }
            }
        });
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> ((c.rawDescription.contains("Apply") ||c.rawDescription.contains("apply") ||c.rawDescription.contains("applies") ||c.rawDescription.contains("Lick") ||c.rawDescription.contains("Debuff") ||c.rawDescription.contains("Steal") || c.name.contains("Disarm") || c.name.contains("Choke") || c.name.contains("Talk to the Hand") || c.name.contains("Cursed Wail") || c.name.contains("Undervolt"))
            ) && c.rarity == AbstractCard.CardRarity.UNCOMMON);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, "Special Bonus Card!");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
