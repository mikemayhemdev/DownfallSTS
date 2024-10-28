package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SaveForLater extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("SaveForLater");

    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 2;

    public SaveForLater() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "SaveForLater.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        ArrayList<AbstractCard> handCards = new ArrayList<>(p.hand.group);
        handCards.remove(this);

        if (!handCards.isEmpty()) {
            if (upgraded) {
                AbstractDungeon.handCardSelectScreen.open("Choose a card to Retain", 1, false, false);
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                                c.retain = true;
                            }
                            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                        }
                        isDone = true;
                    }
                });
            } else {
                AbstractCard randomCard = handCards.get(AbstractDungeon.cardRandomRng.random(handCards.size() - 1));
                randomCard.retain = true;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
