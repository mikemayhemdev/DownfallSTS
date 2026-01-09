package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import sneckomod.SneckoMod;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class ComboString extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("ComboString");

    // Card constants
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int COST = 1;

    private static int SOFTLOCK = 0;

    public ComboString() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        this.tags.add(SneckoMod.GIFT);
        SneckoMod.loadJokeCardImage(this, "ComboString.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID) && (SOFTLOCK < 100)) {
                SOFTLOCK++;
                return true;
            }
        }
        if (SOFTLOCK >= 100) {
            System.out.println("SOFTLOCK DETECTED!!!");
        }
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int offclassCardsPlayed = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(card -> card.color != AbstractDungeon.player.getCardColor())
                .count();

        for (int i = 0; i < offclassCardsPlayed; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int offclassCardsPlayed = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(card -> card.color != AbstractDungeon.player.getCardColor())
                .count();

        updateDescription(offclassCardsPlayed);
    }

    private void updateDescription(int offclassCardsPlayed) {
        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0] + offclassCardsPlayed + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.UNCOMMON);

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
