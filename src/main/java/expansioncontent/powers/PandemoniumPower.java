package expansioncontent.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PandemoniumPower extends AbstractPower {
    public static final String POWER_ID = "expansioncontent:PandemoniumPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PandemoniumPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.updateDescription();
        this.loadRegion("mayhem");
        priority = -999;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for (int i = 0; i < this.amount; ++i) {
            if (!AbstractDungeon.player.discardPile.isEmpty()) {
                AbstractCard cardToPlay = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                AbstractDungeon.player.discardPile.removeCard(cardToPlay);
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractMonster randomMonster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        cardToPlay.freeToPlayOnce = true;
                        cardToPlay.applyPowers();
                        cardToPlay.calculateCardDamage(randomMonster);

                        AbstractDungeon.player.limbo.addToBottom(cardToPlay);
                        cardToPlay.current_y = -200.0F * Settings.scale;
                        cardToPlay.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
                        cardToPlay.target_y = (Settings.HEIGHT / 2.0F);
                        cardToPlay.calculateCardDamage(randomMonster);

                        AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(cardToPlay, randomMonster, cardToPlay.energyOnUse));
                        this.addToTop(new UnlimboAction(cardToPlay));
                        this.isDone = true;
                    }
                });

            }
        }
    }
}
