package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

import java.util.ArrayList;
import java.util.Collections;

public class SoulCleanse extends AbstractSneckoCard {

    public final static String ID = makeID("SoulCleanse");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SoulCleanse() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MuddleHandAction());
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> myList = new ArrayList<>(AbstractDungeon.player.hand.group);
                Collections.shuffle(myList, AbstractDungeon.cardRandomRng.random);
                for (AbstractCard card : myList) {
                    if (card.cost == 3) {
                        card.cost = 0;// 35
                        card.costForTurn = card.cost;// 36
                        card.isCostModified = true;// 37

                        card.freeToPlayOnce = false;// 39
                        break;
                    }
                }
            }
        });
        if (upgraded) atb(new DrawCardAction(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}