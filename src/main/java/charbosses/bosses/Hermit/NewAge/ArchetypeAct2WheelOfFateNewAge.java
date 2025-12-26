package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnGoodInstincts;
import charbosses.cards.colorless.EnHandOfGreedHermitNecro;
import charbosses.cards.colorless.EnPanacea;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnNecronomicurse;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitWheelOfFortune;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static charbosses.cards.EnemyCardGroup.HAND_HEIGHT_OFFSET;
import static charbosses.cards.EnemyCardGroup.HAND_ROW_LENGTH;

public class ArchetypeAct2WheelOfFateNewAge extends ArchetypeBaseIronclad {
    public ArrayList<AbstractBossCard> mockDeck = new ArrayList<>();

    public ArchetypeAct2WheelOfFateNewAge() {
        super("HERMIT_WHEEL_ARCHETYPE", "Wheel of Fortune");

        maxHPModifier += 195;
        actNum = 2;
        bossMechanicName = HermitWheelOfFortune.NAME;
        bossMechanicDesc = HermitWheelOfFortune.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        reshuffle();

        super.addedPreBattle();

        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitWheelOfFortune(p), 2));
    }


    private void reshuffle() {
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        addCardToDeck(new EnDefendHermit(), false);
        addCardToDeck(new EnStrikeHermit(), true);
        addCardToDeck(new EnDefendHermit(), false);

        addCardToDeck(new EnDesperado(), false);
        addCardToDeck(new EnStrikeHermit(), true);
        addCardToDeck(new EnFlashPowder(), false);

        addCardToDeck(new EnWideOpen(), false);
        addCardToDeck(new EnMaintenance(), extraUpgrades);
        addCardToDeck(new EnGoodInstincts(), false);

        addCardToDeck(new EnSwiftStrike(), false);
        addCardToDeck(new EnPanacea(), false);
        addCardToDeck(new EnShortFuseNecro(), false);
        addCardToDeck(new EnNecronomicurse(), false);
    }

    private void addCardToDeck(AbstractBossCard card, boolean upgrade) {
        if (upgrade) card.upgrade();
        mockDeck.add(card);
    }

    public void removeCardFromDeck(UUID cardUuid) {
        Iterator<AbstractBossCard> var = mockDeck.iterator();
        AbstractBossCard c;
        while (var.hasNext()) {
            c = var.next();
            if (c.uuid == cardUuid) {
                mockDeck.remove(c);
                return;
            }
        }
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

    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_Necronomicon());
        addRelic(new CBR_StrangeSpoon());
        addRelic(new CBR_RedMask());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AbstractCard target = getNextCard();
            cardsList.add(target);

            if (target.cardID.equals(EnNecronomicurse.ID)) {
                AbstractCharBoss.boss.getPower(HermitWheelOfFortune.POWER_ID).onSpecificTrigger();
            }
        }
        if (AbstractCharBoss.boss instanceof CharBossHermit) {
            CharBossHermit.previewCard = mockDeck.get(0).makeStatEquivalentCopy();
        }
        return cardsList;
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

        if (next.cardID.equals(EnNecronomicurse.ID)) {
            AbstractCharBoss.boss.getPower(HermitWheelOfFortune.POWER_ID).onSpecificTrigger();
        }

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

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_StoneCalendar());
    }
}