package sneckomod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class MakeshiftBlade extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("MakeshiftBlade");

    // Card constants
    private static final int DAMAGE = 16;
    private static final int COST = 1;
    private static final int MAGIC = 3; // Initial debuff requirement
    private static final int UPGRADE_MAGIC = -1; // Reduces debuff requirement by 1

    public MakeshiftBlade() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "MakeshiftBlade.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (m != null && m.powers.stream().filter(power -> power.type == AbstractPower.PowerType.DEBUFF).count() >= magicNumber) {
            addToBot(new DrawCardAction(p, 3));
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(
                    c -> c.rarity == AbstractCard.CardRarity.UNCOMMON &&
                            (c.rawDescription.contains("Weak") || c.rawDescription.contains("Vulnerable") || c.rawDescription.contains("Bruise") || c.rawDescription.contains("Soulburn") || c.rawDescription.contains("Goop") || c.rawDescription.contains("Doom"))
            );
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
