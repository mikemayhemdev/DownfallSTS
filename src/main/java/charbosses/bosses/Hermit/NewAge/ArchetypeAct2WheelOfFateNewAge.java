package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnHandOfGreedHermitNecro;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnNecronomicurse;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitWheelOfFortune;
import charbosses.relics.CBR_Necronomicon;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_PenNib;
import charbosses.relics.CBR_PhilosopherStone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.cards.Strike_Hermit;

import java.util.ArrayList;

import static charbosses.cards.EnemyCardGroup.HAND_HEIGHT_OFFSET;
import static charbosses.cards.EnemyCardGroup.HAND_ROW_LENGTH;

public class ArchetypeAct2WheelOfFateNewAge extends ArchetypeBaseIronclad {
    public ArrayList<AbstractCard> mockDeck = new ArrayList<>();

    public ArchetypeAct2WheelOfFateNewAge() {
        super("HERMIT_WHEEL_ARCHETYPE", "Wheel of Fortune");

        maxHPModifier += 195;
        actNum = 1;
        bossMechanicName = HermitWheelOfFortune.NAME;
        bossMechanicDesc = HermitWheelOfFortune.DESC[0] + HermitWheelOfFortune.CARDS_TO_STR + HermitWheelOfFortune.DESC[1];
    }

    @Override
    public void addedPreBattle() {
        reshuffle();

        super.addedPreBattle();

        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitWheelOfFortune(p), 1));
    }

    public boolean usedGestalt = false;
    public int maintenanceLevels = 0;

    private void reshuffle() {
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        AbstractCard show = new EnShowdown();
        show.upgrade();
        mockDeck.add(show);
        AbstractCard strik = new EnFreeStrikeHermit();
        strik.upgrade();
        mockDeck.add(strik);
        AbstractCard strik2 = new EnFreeStrikeHermit();
        mockDeck.add(strik2);

        mockDeck.add(new EnDefendHermit());
        mockDeck.add(new EnDesperado());
        AbstractCard wide = new EnWideOpen();
        wide.upgrade();
        mockDeck.add(wide);

        mockDeck.add(new EnHoleUp());
        if (usedGestalt) {
            mockDeck.add(new EnInjury());
        } else {
            mockDeck.add(new EnGestalt());
        }
        mockDeck.add(new EnItchyTriggerAct2());

        mockDeck.add(new EnHandOfGreedHermitNecro());
        AbstractCard virt = new EnVirtue();
        virt.upgrade();
        mockDeck.add(virt);
        AbstractCard maint = new EnMaintenance();
        maint.upgrade();
        mockDeck.add(maint);

        mockDeck.add(new EnNecronomicurse());
    }

    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_PenNib());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_Necronomicon());
    }

    public static void upgradeStrikeOrDefendManually(AbstractCard c) {
        if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                c.baseDamage += 3;
                c.upgradedDamage = true;
            } else if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                c.baseBlock += 3;
                c.upgradedBlock = true;
            }
            String NAME;
            if (c instanceof EnStrikeHermit || c instanceof EnFreeStrikeHermit) {
                NAME = Strike_Hermit.NAME;
            } else {
                NAME = EnDefendHermit.cardStrings.NAME;
            }
            c.timesUpgraded++;
            c.upgraded = true;
            if (c.timesUpgraded > 1)
                c.name = NAME + "+" + c.timesUpgraded;
            else
                c.name = NAME + "+";
            c.applyPowers();
        }
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AbstractCard target = getNextCard();
            cardsList.add(target);
        }
        if (cardsList.stream().anyMatch(q -> q.cardID.equals(EnItchyTriggerAct2.ID))) {
            for (AbstractCard target : cardsList) {
                if (target instanceof EnFreeStrikeHermit) {
                    target.setCostForTurn(0);
                }
            }
        }
        if (AbstractCharBoss.boss instanceof CharBossHermit) {
            CharBossHermit.previewCard = mockDeck.get(0).makeStatEquivalentCopy();
        }
        return cardsList;
    }

    protected AbstractCard getNextCard() {
        AbstractCard q = mockDeck.remove(0);
        AbstractCard x = q.makeStatEquivalentCopy();
        mockDeck.add(mockDeck.size(), q);
        int cardsinrow = Math.min(AbstractCharBoss.boss.hand.group.size() - HAND_ROW_LENGTH * (int) Math.floor((float) AbstractCharBoss.boss.hand.group.size() / (float) HAND_ROW_LENGTH), HAND_ROW_LENGTH);
        float widthspacing = AbstractCard.IMG_WIDTH_S + 100.0f * Settings.scale;
        x.current_x = Settings.WIDTH * .9F - ((cardsinrow + 0.5f) * (widthspacing * AbstractBossCard.HAND_SCALE)) + (widthspacing * AbstractBossCard.HAND_SCALE) * (AbstractCharBoss.boss.hand.group.size() % HAND_ROW_LENGTH);
        x.current_y = Settings.HEIGHT * HAND_HEIGHT_OFFSET + (AbstractCard.IMG_HEIGHT_S * AbstractBossCard.HAND_SCALE) * ((float) Math.floor(((float) AbstractCharBoss.boss.hand.group.size()) / (float) HAND_ROW_LENGTH) + (AbstractCharBoss.boss.hand.group.size() > HAND_ROW_LENGTH ? 0.0f : 1.0f));
        return x;
    }

    public void reInitializeHand() {
        if (!AbstractCharBoss.boss.isDeadOrEscaped()) {
            if (AbstractCharBoss.boss.hand != null) {
                if (AbstractCharBoss.boss.hand.size() > 0) {
                    AbstractCard bot = AbstractCharBoss.boss.hand.getBottomCard();
                    AbstractCharBoss.boss.hand.removeCard(bot);
                    if (bot instanceof EnShowdown || bot instanceof EnItchyTriggerAct2) {
                        ((AbstractHermitBossCard) bot).onSpecificTrigger();
                    }
                    AbstractCharBoss.boss.hand.addToTop(getNextCard());
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
            }
        }
    }

    @Override
    public void initializeBonusRelic() {

    }
}