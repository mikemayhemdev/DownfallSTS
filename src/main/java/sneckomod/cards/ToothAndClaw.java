package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class ToothAndClaw extends AbstractSneckoCard {

    public final static String ID = makeID("ToothAndClaw");

    // this card exists

    public ToothAndClaw() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        SneckoMod.loadJokeCardImage(this, "ToothAndClaw.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        for (int i = 0; i < findSuitinHand(); i++) {
            AbstractCard s = new Shiv();
            if (this.upgraded) {
                s.upgrade();
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(s));
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.UNCOMMON);

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

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}