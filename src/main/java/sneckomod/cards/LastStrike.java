package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.HashSet;

public class LastStrike extends AbstractSneckoCard {
    public static final String ID = makeID("LastStrike");

    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;

    // strike tracking
    private HashSet<String> uniqueStrikeNames;
    private HashSet<String> uniqueStrikeIDs;
    private boolean hasPlayedOnce;

    public LastStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        tags.add(CardTags.STRIKE);
        SneckoMod.loadJokeCardImage(this, "LastStrike.png");

        // set everything to zero
        resetMultiplierTracking();
    }

    private void resetMultiplierTracking() {
        uniqueStrikeNames = new HashSet<>();
        uniqueStrikeIDs = new HashSet<>();
        hasPlayedOnce = false;
    }

    @Override
    public void atBattleStart() {
        resetMultiplierTracking();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // don't immediately add last strike as you play it for the first time
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);
        int multiplier = (int) Math.pow(2, uniqueStrikesCount);

        int totalDamage = baseDamage * multiplier;
        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // NOW, add last strike.
        if (!hasPlayedOnce) {
            uniqueStrikeNames.add(this.name);
            uniqueStrikeIDs.add(this.cardID);
            hasPlayedOnce = true;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int uniqueStrikesCount = calculateDamageMultiplier();
        int multiplier = (int) Math.pow(2, uniqueStrikesCount);

        // default to base damage
        if (uniqueStrikesCount == 0) {
            multiplier = 1;
        }

        // update card text
        this.damage = baseDamage * multiplier;
        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0] + multiplier + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    private int calculateDamageMultiplier() {
        return calculateDamageMultiplier(true);
    }

    private int calculateDamageMultiplier(boolean excludeThisCard) {
        uniqueStrikeNames.clear();
        uniqueStrikeIDs.clear();

        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.cardID.contains("Strike")
                    && (!excludeThisCard || card != this)
                    && !uniqueStrikeNames.contains(card.name)
                    && !uniqueStrikeIDs.contains(card.cardID)) {
                uniqueStrikeNames.add(card.name); // dupe tracking by name (strike from ironclad vs strike from defect, etc. meant so that strikes generated from high caliber don't count as extra, they're all deal 6 damage starter strikes)
                uniqueStrikeIDs.add(card.cardID); // dupe tracking by id for cross mod compat
            }
        }
        return uniqueStrikeIDs.size();
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity != CardRarity.BASIC && c.hasTag(CardTags.STRIKE));

            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, "Special Bonus Card!");
    }

    private boolean cardListDuplicate(ArrayList<AbstractCard> cardsToReward, AbstractCard newCard) {
        for (AbstractCard card : cardsToReward) {
            if (card.cardID.equals(newCard.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
