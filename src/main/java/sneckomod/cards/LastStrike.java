package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
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

    // Constants
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 6;

    // Strike tracking
    private HashSet<String> uniqueStrikeIDs;
    private boolean hasPlayedOnce;

    public LastStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        tags.add(CardTags.STRIKE);
        SneckoMod.loadJokeCardImage(this, "LastStrike.png");

        resetMultiplierTracking();
    }

    private void resetMultiplierTracking() {
        uniqueStrikeIDs = new HashSet<>();
        hasPlayedOnce = false;
    }

    @Override
    public void atBattleStart() {
        resetMultiplierTracking();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);

        for (int i = 0; i < uniqueStrikesCount + 1; ++i) {
            this.addToBot(new ExpungeVFXAction(m));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

        if (!hasPlayedOnce) {
            uniqueStrikeIDs.add(this.cardID);
            hasPlayedOnce = true;
        }

        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0] + uniqueStrikesCount + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);

        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0] + uniqueStrikesCount + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    private int calculateDamageMultiplier(boolean excludeThisCard) {
        uniqueStrikeIDs.clear();

        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.cardID.contains("Strike")
                    && (!excludeThisCard || card != this)
                    && !uniqueStrikeIDs.contains(card.cardID)) {
                uniqueStrikeIDs.add(card.cardID);
            }
        }
        return uniqueStrikeIDs.size();
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity != CardRarity.BASIC && ((c.hasTag(CardTags.STRIKE) || c.name.contains("High-Caliber"))));

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
