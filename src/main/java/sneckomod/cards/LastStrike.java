package sneckomod.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import collector.cards.BramblesparKindling;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
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
    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    private static int SOFTLOCK = 0;

    // Strike tracking
    private HashSet<String> uniqueStrikeIDs;
    private boolean hasPlayedOnce;

    public LastStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        tags.add(CardTags.STRIKE);
        SneckoMod.loadJokeCardImage(this, "LastStrike.png");
        this.tags.add(SneckoMod.GIFT);
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

        updateDescription(uniqueStrikesCount);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int uniqueStrikesCount = calculateDamageMultiplier(!hasPlayedOnce);
        updateDescription(uniqueStrikesCount);
    }

    private int calculateDamageMultiplier(boolean excludeThisCard) {
        uniqueStrikeIDs.clear();
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
        if (upgraded) {
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
                    c -> c.rarity != CardRarity.BASIC && (c.hasTag(CardTags.STRIKE) || c.cardID.equals(HighCaliber.ID) || c.cardID.equals(BramblesparKindling.ID))
            );

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            if (!cardListDuplicate(cardsToReward, newCard)) {
                SOFTLOCK = 0;
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    private boolean cardListDuplicate(ArrayList<AbstractCard> cardsToReward, AbstractCard newCard) {
        for (AbstractCard card : cardsToReward) {
            if (card.cardID.equals(newCard.cardID) && (SOFTLOCK < 100)) {
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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
