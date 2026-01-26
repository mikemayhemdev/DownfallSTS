package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class DangerNoodle extends AbstractSneckoCard implements OnObtainCard {

    public static final String ID = makeID("DangerNoodle");

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;
    private static int SOFTLOCK = 0;

    public DangerNoodle() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        this.tags.add(SneckoMod.GIFT);
        SneckoMod.loadJokeCardImage(this, "DangerNoodle.png");
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
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        String stuff;
        if(Settings.language == Settings.GameLanguage.DEU){
            stuff = "irsst";
        } else {
            stuff = BaseMod.getKeywordProper("sneckomod:muddle");
        }
        addToBot(new SelectCardsInHandAction(1, stuff,
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleAction(card));
                    }
                }
        ));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.cost >= 3);

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
