package sneckomod.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import hermit.cards.HighCaliber;
import sneckomod.SneckoMod;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;
import java.util.HashSet;

public class LastStrike extends AbstractSneckoCard {
    public static final String ID = SneckoMod.makeID("LastStrike");

    // Constants
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;

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
        // Calculate multiplier for this use
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);

        // Apply damage based on multiplier
        for (int i = 0; i < uniqueStrikesCount + 1; ++i) {
            this.addToBot(new ExpungeVFXAction(m));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

        // Track LastStrike after first play to exclude itself in future calculations
        if (!hasPlayedOnce) {
            uniqueStrikeIDs.add(this.cardID);
            hasPlayedOnce = true;
        }

        updateDescription(uniqueStrikesCount);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // Calculate the multiplier without including this card if not yet played
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);

        // Update the description to show the multiplier dynamically
        updateDescription(uniqueStrikesCount);
    }

    private int calculateDamageMultiplier(boolean excludeThisCard) {
        uniqueStrikeIDs.clear();

        // Track unique Strike cards played this combat
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.hasTag(CardTags.STRIKE)
                    && (!excludeThisCard || card != this)) {
                uniqueStrikeIDs.add(card.cardID);
            }
        }
        return uniqueStrikeIDs.size();
    }

    private void updateDescription(int uniqueStrikesCount) {
        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0] + uniqueStrikesCount + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        if(upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(
                    c -> c.rarity != CardRarity.BASIC && (c.hasTag(CardTags.STRIKE) || c.cardID.equals(HighCaliber.ID))
            );

            if (((newCard.type == AbstractCard.CardType.SKILL) && (AbstractDungeon.player.hasRelic(ToxicEgg2.ID)) ||
                    ((newCard.type == AbstractCard.CardType.ATTACK) && (AbstractDungeon.player.hasRelic(MoltenEgg2.ID)) ||
                            (newCard.type == AbstractCard.CardType.POWER) && (AbstractDungeon.player.hasRelic(FrozenEgg2.ID)) ||
                            AbstractDungeon.player.hasRelic(UnknownEgg.ID)))) {
                newCard.upgrade();
            }

            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
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
