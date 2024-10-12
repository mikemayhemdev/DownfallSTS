package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.FlameAffectAllEnemiesPower;

public class MayhemGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("mayhem.png"));
    public static Texture bruhB = TextureLoader.getTexture(HexaMod.makeUIPath("mayhemBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("card.png"));

    private final String  ID = "hexamod:MayhemGhostflame";
    private final String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

    private Color flameColor = new Color(164F/255F, 210F/255F, 249F/255F, 1F);
    private Color activeColor = new Color(164F/255F * 0.5F, 210F/255F * 0.5F, 249F/255F * 0.5F, 1F);

    public MayhemGhostflame(float x, float y) {
        super(x, y);

        this.magic = 1;
        //this.textColor = new Color(.5F,1F,1F,1F);
        this.triggersRequired = 1;

        this.effectIconXOffset = 80F;
        this.effectIconYOffset = -20F;

    }

    @Override
    public int getActiveFlamesTriggerCount() {
        return 1;
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {
            public void update() {
                if(AbstractDungeon.player.hasPower(FlameAffectAllEnemiesPower.POWER_ID)){
                    if(getEffectCount() > 0) {
                        this.addToBot(new ScryAction(getEffectCount()));
                    }
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if(m == null) {
                        this.isDone = true;
                        return;
                    }
                    if(!AbstractDungeon.player.drawPile.isEmpty()) {
                        AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
                        this.addToBot(new PlayTopCardAction(m, false));
                        for (int i = 0; i < AbstractDungeon.player.getPower(FlameAffectAllEnemiesPower.POWER_ID).amount - 1; i++) {
                            AbstractCard tmp = c.makeSameInstanceOf();
                            AbstractDungeon.player.limbo.addToBottom(tmp);
                            tmp.purgeOnUse = true;
                            tmp.calculateCardDamage(m);
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), false);
                        }
                    }

                }else {
                    this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                    if(getEffectCount() > 0) {
                        this.addToTop(new ScryAction(getEffectCount()));
                    }
                }

                this.isDone = true;
            }
        });
    }

    @Override
    public void advanceTrigger(AbstractCard c) {
        if (!charged){
            advanceTriggerAnim();
            charge();
        }
    }

    public int getEffectCount() {
        int x = magic;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return x;
    }


    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public Texture getHelperTextureBright() {
        return bruhB;
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
    }

    @Override
    public String returnHoverHelperText() {
        return getEffectCount() + "";
    }

    @Override
    public String getName(){ return NAME;}

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = DESCRIPTIONS[0];
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[1];
        } else {
            s = s + DESCRIPTIONS[2];
        }
        s = s + DESCRIPTIONS[3] + getEffectCount() + DESCRIPTIONS[4];
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[5];
        }
        return s;
    }


    public Color getFlameColor() {
        return activeColor.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        //return activeColor.cpy();
        return Color.PURPLE.cpy();
    }
}
