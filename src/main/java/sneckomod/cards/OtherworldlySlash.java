package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class OtherworldlySlash extends AbstractSneckoCard implements OnObtainCard {

    public final static String ID = makeID("OtherworldlySlash");

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static int SOFTLOCK = 0;

    public OtherworldlySlash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "OtherworldlySlash.png");
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
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        boolean playedOffClassCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .anyMatch(card -> card.color != this.color);

        if (playedOffClassCard) {
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
    }

    @Override
    public void triggerOnGlowCheck() { // it glows now.
        boolean playedOffClassCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .anyMatch(card -> card.color != this.color);
        if (playedOffClassCard) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.COMMON);

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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
