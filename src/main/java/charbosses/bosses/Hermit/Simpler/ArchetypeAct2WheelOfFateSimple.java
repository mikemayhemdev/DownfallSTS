package charbosses.bosses.Hermit.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnNecronomicurse;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitWheelOfFortune;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static charbosses.cards.EnemyCardGroup.HAND_HEIGHT_OFFSET;
import static charbosses.cards.EnemyCardGroup.HAND_ROW_LENGTH;

public class ArchetypeAct2WheelOfFateSimple extends ArchetypeBaseIronclad {
    public ArrayList<AbstractBossCard> mockDeck = new ArrayList<>();


    public ArchetypeAct2WheelOfFateSimple() {
        super("HERMIT_WHEEL_ARCHETYPE", "Wheel of Fortune");

        maxHPModifier += 198;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        reshuffle();

    }

    public AbstractBossCard getCardFromDeck(UUID cardUuid) {
        Iterator<AbstractBossCard> var = mockDeck.iterator();
        AbstractBossCard c;
        while (var.hasNext()) {
            c = var.next();
            if (c.uuid == cardUuid) {
                return c;
            }
        }
        return null;
    }

    private void reshuffle() {
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        addCardToDeck(new EnDefendHermit(), false);
        addCardToDeck(new EnStrikeHermit(), true);
        addCardToDeck(new EnDoubt(), false);

        addCardToDeck(new EnGlare(), false);
        addCardToDeck(new EnDesperado(), true);
        addCardToDeck(new EnPurgatory(), false);

        addCardToDeck(new EnWideOpen(), false);
        addCardToDeck(new EnDefendHermit(), extraUpgrades);
        addCardToDeck(new EnStrikeHermit(), false);

        addCardToDeck(new EnHoleUp(), false);
        addCardToDeck(new EnDefendHermit(), false);
        addCardToDeck(new EnLowProfile(), false);

        addCardToDeck(new EnStrikeHermit(), false);
        addCardToDeck(new EnReprieve(), false);
        addCardToDeck(new EnDissolve(), false);

        addCardToDeck(new EnMemento(), false);
        addCardToDeck(new EnGoldenBullet(), false);
    }

    private void addCardToDeck(AbstractBossCard card, boolean upgrade) {
        if (upgrade) card.upgrade();
        mockDeck.add(card);
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {

        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        if (turn == 0){
            cardsList.add(new EnRevolver());
            cardsList.add(new EnDetermination());
            turn++;
        }
        else {
        for (int i = 0; i < 3; i++) {
            AbstractCard target = getNextCard();
            cardsList.add(target);
        }
        if (AbstractCharBoss.boss instanceof CharBossHermit) {
            CharBossHermit.previewCard = mockDeck.get(0).makeStatEquivalentCopy();
        }
        }

        return cardsList;
    }


    public void reInitializeHand() {
        if (AbstractCharBoss.boss.isDeadOrEscaped()) {
            return;
        }
        if (AbstractCharBoss.boss.hand == null || AbstractCharBoss.boss.hand.size() == 0) {
            return;
        }

        AbstractCard bot = AbstractCharBoss.boss.hand.getBottomCard();
        AbstractCharBoss.boss.hand.removeCard(bot);
//        if (bot instanceof EnShowdown) {
//            ((AbstractHermitBossCard) bot).onSpecificTrigger();
//        }
        AbstractCard next = getNextCard();
        AbstractCharBoss.boss.hand.addToTop(next);

        AbstractCharBoss.boss.hand.refreshHandLayout();
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractCharBoss.boss != null && !AbstractCharBoss.boss.isDead && !AbstractCharBoss.boss.isDying) {
                    if (AbstractCharBoss.boss.hand != null) {
                        AbstractCharBoss.boss.hand.group.stream().forEach(q -> ((AbstractBossCard) q).bossDarken());
                        int budget = AbstractCharBoss.boss.energyPanel.getCurrentEnergy();
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            if (c.costForTurn <= budget && c.costForTurn != -2 && c instanceof AbstractBossCard) {
                                ((AbstractBossCard) c).createIntent();
                                ((AbstractBossCard) c).bossLighten();
                                budget -= c.costForTurn;
                                budget += ((AbstractBossCard) c).energyGeneratedIfPlayed;
                                if (budget < 0) budget = 0;
                            } else if (c.costForTurn == -2 && c.type == AbstractCard.CardType.CURSE && c.color == AbstractCard.CardColor.CURSE) {
                                ((AbstractBossCard) c).bossLighten();
                            }
                        }
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            AbstractBossCard cB = (AbstractBossCard) c;
                            cB.refreshIntentHbLocation();
                            c.applyPowers();
                        }
                        AbstractCharBoss.boss.hand.refreshHandLayout();
                    }
                }
            }
        });
        if (AbstractCharBoss.boss instanceof CharBossHermit) {
            CharBossHermit.previewCard = mockDeck.get(0).makeStatEquivalentCopy();
        }
    }


    protected AbstractCard getNextCard() {
        AbstractBossCard q = mockDeck.remove(0);
        AbstractCard x = q.makeSameInstanceOf();
        mockDeck.add(mockDeck.size(), q);
        int cardsinrow = Math.min(AbstractCharBoss.boss.hand.group.size() - HAND_ROW_LENGTH * (int) Math.floor((float) AbstractCharBoss.boss.hand.group.size() / (float) HAND_ROW_LENGTH), HAND_ROW_LENGTH);
        float widthspacing = AbstractCard.IMG_WIDTH_S + 100.0f * Settings.scale;
        x.current_x = Settings.WIDTH * .9F - ((cardsinrow + 0.5f) * (widthspacing * AbstractBossCard.HAND_SCALE)) + (widthspacing * AbstractBossCard.HAND_SCALE) * (AbstractCharBoss.boss.hand.group.size() % HAND_ROW_LENGTH);
        x.current_y = Settings.HEIGHT * HAND_HEIGHT_OFFSET + (AbstractCard.IMG_HEIGHT_S * AbstractBossCard.HAND_SCALE) * ((float) Math.floor(((float) AbstractCharBoss.boss.hand.group.size()) / (float) HAND_ROW_LENGTH) + (AbstractCharBoss.boss.hand.group.size() > HAND_ROW_LENGTH ? 0.0f : 1.0f));
        return x;
    }


}