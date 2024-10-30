package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.HashSet;

public class LastStrike extends AbstractSneckoCard {
    public static final String ID = makeID("LastStrike");

    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;

    public LastStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        tags.add(CardTags.STRIKE);
        SneckoMod.loadJokeCardImage(this, "LastStrike.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int damageMultiplier = calculateDamageMultiplier();
        int totalDamage = ((damageMultiplier+1) * 2) * baseDamage;
        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int uniqueStrikesCount = calculateDamageMultiplier();
        int multiplier = ((uniqueStrikesCount + 1) * 2);
        this.rawDescription = cardStrings.DESCRIPTION + " NL (Deals " + multiplier + "x damage.)";
        initializeDescription();
    }

    private int calculateDamageMultiplier() {
        HashSet<String> uniqueStrikeNames = new HashSet<>();
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.cardID.contains("Strike")) {
                uniqueStrikeNames.add(card.cardID);
            }
        }
        return uniqueStrikeNames.size();
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.cardID.contains("Strike"));
            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }
        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, "Choose an Offclass Strike!");
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
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
