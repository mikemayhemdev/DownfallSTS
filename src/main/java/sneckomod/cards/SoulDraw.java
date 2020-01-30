package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class SoulDraw extends AbstractSneckoCard {

    public final static String ID = makeID("SoulDraw");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public SoulDraw() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = getRandomNum(0, magicNumber);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() < x) {
                } else if (p.drawPile.isEmpty() || p.drawPile.size() < x) {
                    att(new DrawCardAction(p, magicNumber));
                    for (int i = 0; i < x; i++) {
                        int r = i;
                        att(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                AbstractCard q = p.drawPile.getNCardFromTop(r);
                                att(new MuddleAction(q));
                            }
                        });
                    }
                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());// 34
                } else {
                    isDone = true;
                    att(new DrawCardAction(p, magicNumber));
                    for (int i = 0; i < x; i++) {
                        int r = i;
                        att(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                AbstractCard q = p.drawPile.getNCardFromTop(r);
                                att(new MuddleAction(q));
                            }
                        });
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}